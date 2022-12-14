import { Component, ViewChild, ElementRef, OnInit } from '@angular/core';
import { webSocket } from 'rxjs/webSocket';
import { UserService } from '../service/user.service';
import { CookieService } from 'ngx-cookie-service';
import { ChatList } from '../model/chats.list';
import { MessageList } from '../model/message.list';
import { Router } from '@angular/router';
import { AccountService } from '../account-service/account.service';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css'],
})
export class HomepageComponent {
  webSocket = webSocket<{
    chatId: number;
    sender: string;
    receiver: string;
    content: string;
  }>('ws://localhost:8080/ws');

  currentChatID: string;

  chatList: {
    [key: string]: {
      chat: {
        chatId: number;
        sender: string;
        content: string;
      }[];
      participant: string;
    };
  };

  chatMenu: {
    username: string;
    message: string;
    id: string;
    selected: boolean;
    unread: number;
    dialogWith: string;
  }[] = [];

  yourMessage: string = '';

  loadFlag = false;

  @ViewChild('scrollable') private myScrollContainer: ElementRef =
    new ElementRef<any>('scrollable');

  constructor(
    private userService: UserService,
    private cookieService: CookieService,
    private router: Router,
    public accountService: AccountService
  ) {
    this.currentChatID = '';
    this.chatList = {};
  }

  ngOnInit() {
    if (this.accountService.isDefault()) {
      this.router.navigate(['/signin']);
    }
    console.log('Requesting Chat List...');
    this.userService.getUserChats().subscribe((chatList: ChatList) => {
      console.log('Got Response:', chatList);
      this.currentChatID = this.cookieService.get('currentChatID');
      if (chatList.data) {
        for (const chat of chatList.data) {
          if (this.chatMenu.length === 0 && this.currentChatID === '') {
            this.currentChatID = String(chat.chatId);
            this.cookieService.set('currentChatID', this.currentChatID);
          }
          const you = this.cookieService.get('username');
          const them =
            chat.participants[0] === you
              ? chat.participants[1]
              : chat.participants[0];
          const chatMenuItem = {
            username: '',
            message: '',
            date: '',
            id: String(chat.chatId),
            selected: this.currentChatID === String(chat.chatId),
            unread: 0,
            dialogWith: them,
          };
          this.chatList[chat.chatId] = {
            chat: [],
            participant: them,
          };
          console.log(
            'Requesting Messages in Chat:',
            chat.chatId,
            'With Users:',
            chat.participants
          );
          this.userService
            .getMessagesInChat(chat.chatId)
            .subscribe((messageList: MessageList) => {
              console.log('Response:', messageList);
              if (messageList.data) {
                this.chatList[chat.chatId] = {
                  chat: messageList.data,
                  participant: them,
                };
                console.log('Them:', them);
                const len = messageList.data.length;
                const last = len > 0 ? messageList.data[len - 1].content : '';
                chatMenuItem.username =
                  len > 0 ? messageList.data[len - 1].sender : '';
                chatMenuItem.message = last;
                chatMenuItem.date = '';
                chatMenuItem.id = String(chat.chatId);
                chatMenuItem.selected =
                  this.currentChatID === String(chat.chatId);
                chatMenuItem.unread = 0;
              }
            });
          this.chatMenu.push(chatMenuItem);
        }
      }
      if (this.chatMenu.length == 0) {
        this.currentChatID = '';
      }
      this.loadFlag = true;
      console.log('Selected Chat ID:', this.currentChatID);
      console.log('Generated ChatList:', this.chatList);
    });

    this.webSocket.asObservable().subscribe((data) => {
      console.log('Received from WebSocket:', data);
      if (String(data.chatId) in this.chatList) {
        this.addMessage(String(data.chatId), data.sender, data.content);
      } else if (data.receiver === this.cookieService.get('username')) {
        this.chatList[data.chatId] = {
          chat: [
            {
              chatId: data.chatId,
              sender: data.sender,
              content: data.content,
            },
          ],
          participant: data.sender,
        };
        this.chatMenu.push({
          username: data.sender,
          message: data.content,
          id: String(data.chatId),
          selected: false,
          unread: 1,
          dialogWith: data.sender,
        });
        this.sortMenu();
      }
    });
    this.sortMenu();
  }

  addMessage(id: string, sender: string, content: string) {
    this.chatList[id].chat.push({
      chatId: Number(id),
      sender: sender,
      content: content,
    });
    for (const chatItem of this.chatMenu) {
      if (chatItem.id === id) {
        if (sender === this.chatList[id].participant) {
          chatItem.unread++;
        } else {
          chatItem.unread = 0;
        }
        chatItem.username = sender;
        chatItem.message = content;
      }
    }
    this.sortMenu();
    this.scrollToBottom();
  }

  renderChat(id: string) {
    this.yourMessage = '';
    this.currentChatID = id;
    this.cookieService.set('currentChatID', id);
    for (const chatItem of this.chatMenu) {
      chatItem.selected = chatItem.id == id;
      if (chatItem.id == id) {
        chatItem.unread = 0;
      }
    }
    this.scrollToBottom();
  }

  handleSendMessage() {
    const msg = this.yourMessage;
    if (msg.length === 0) return;
    this.webSocket.next({
      chatId: Number(this.currentChatID),
      sender: this.cookieService.get('username'),
      receiver: this.chatList[this.currentChatID].participant,
      content: msg,
    });
    this.userService.sendMesage(
      Number(this.currentChatID),
      this.cookieService.get('username'),
      msg
    );
    this.yourMessage = '';
  }

  private scrollToBottom(): void {
    function sleep(time: number) {
      return new Promise((resolve) => setTimeout(resolve, time));
    }
    sleep(100).then(() => {
      try {
        this.myScrollContainer.nativeElement.scrollTop =
          this.myScrollContainer.nativeElement.scrollHeight;
      } catch (err) {}
    });
  }

  shouldRenderMessage(): boolean {
    return this.currentChatID.length == 0 && this.loadFlag;
  }

  sortMenu() {
    this.chatMenu.sort((a: any, b: any): number => {
      return b.unread - a.unread;
    });
  }
}
