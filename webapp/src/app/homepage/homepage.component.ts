import { Component, ViewChild, ElementRef, OnInit } from '@angular/core';
import { webSocket, WebSocketSubject } from 'rxjs/webSocket';
import { UserService } from '../service/user.service';
import { User } from '../model/user';
import { CookieService } from 'ngx-cookie-service';
import { ChatList } from '../model/chats.list';
import { MessageList } from '../model/message.list';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css'],
})
export class HomepageComponent {

  myWebSocket: WebSocketSubject<{
    chatId: number;
    sender: string;
    content: string;
  }> = webSocket('ws://localhost:8080/ws');

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
    date: string;
    id: string;
    selected: boolean;
    unread: number;
    dialogWith: string;
  }[] = [];

  yourMessage: string = '';

  @ViewChild('scrollable') private myScrollContainer: ElementRef =
    new ElementRef<any>('scrollable');

  constructor(private userService: UserService, private cookieService: CookieService) {
    this.currentChatID = this.cookieService.get('currentChatID');
    this.chatList = {};
  }


  ngOnInit() {
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
          const them = (chat.participants[0] === you) ? chat.participants[1] : chat.participants[0];
          const chatMenuItem = {
            username: '',
            message: '',
            date: '',
            id: String(chat.chatId),
            selected: this.currentChatID === String(chat.chatId),
            unread: 0,
            dialogWith: them
          };
          this.chatList[chat.chatId] = {
            chat: [],
            participant: them
          }
          this.userService.getMessagesInChat(chat.chatId).subscribe((messageList: MessageList) => {
            if (messageList.data) {
              this.chatList[chat.chatId] = {
                chat: messageList.data,
                participant: them
              }
              console.log("Them:", them);
              const len = messageList.data.length;
              const last = (len > 0) ? messageList.data[len - 1].content : '';
              chatMenuItem.username = (len > 0) ? messageList.data[len - 1].sender : '';
              chatMenuItem.message = last;
              chatMenuItem.date = '';
              chatMenuItem.id = String(chat.chatId);
              chatMenuItem.selected = this.currentChatID === String(chat.chatId);
              chatMenuItem.unread = 0;
            }
          });
          this.chatMenu.push(chatMenuItem);
        }
      }
      console.log('Selected Chat ID:', this.currentChatID);
      console.log('Generated ChatList:', this.chatList);
    });

    this.myWebSocket.asObservable().subscribe(data => {
      if (String(data.chatId) in this.chatList) {
        this.addMessage(String(data.chatId), data.sender, data.content);
      }
    });
  }

  addMessage(id: string, sender: string, content: string) {
    this.chatList[id].chat.push({
      chatId: Number(id),
      sender: sender,
      content: content
    });
    for (const chatItem of this.chatMenu) {
      if (chatItem.id === id) {
        if (sender === this.chatList[id].participant) {
          chatItem.unread++;
        }
        chatItem.username = sender;
        chatItem.message = content;
        chatItem.date = (new Date(Date.now())).toLocaleTimeString();
      }
    }
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
    console.log('You send Message:', msg);
    if (msg.length === 0) return;
    // this.myWebSocket.next({
    //   chatId: Number(this.currentChatID),
    //   sender: this.cookieService.get('username'),
    //   content: msg
    // });
    this.yourMessage = '';
    this.userService.sendMesage(this.currentChatID, this.cookieService.get('username'), msg);
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
}
