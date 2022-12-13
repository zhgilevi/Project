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
  }> = webSocket('ws://localhost:8000'); // fix url

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
  }[] = [];

  yourMessage: string = '';

  @ViewChild('scrollable') private myScrollContainer: ElementRef =
    new ElementRef<any>('scrollable');

  constructor(private userService: UserService, private cookieService: CookieService) {
    this.currentChatID = '';
    this.chatList = {};
  }


  ngOnInit() {
    try {
      console.log('Token:', this.cookieService.get('token'));
      console.log('Username:', this.cookieService.get('username'));
      console.log('First Name:', this.cookieService.get('fname'));
      console.log('Last Name:', this.cookieService.get('lname'));
      console.log('ID:', this.cookieService.get('id'));
    } catch (err) {
      return;
    }

    this.userService.getUserChats().subscribe((chatList: ChatList) => {
      console.log('Got Response:', chatList);
      this.cookieService.set('currentChatID', '');
      this.currentChatID = '';
      if (chatList.data) {
        for (const chat of chatList.data) {
          this.userService.getMessagesInChat(chat.chatId).subscribe((messageList: MessageList) => {
            if (messageList.data) {
              const you = this.cookieService.get('username');
              this.chatList[chat.chatId] = {
                chat: messageList.data,
                participant: (chat.participants[0] === you) ? you : chat.participants[1]
              }
              if (this.chatMenu.length === 0) {
                this.currentChatID = String(chat.chatId);
                this.cookieService.set('currentChatID', this.currentChatID);
              }
              const len = messageList.data.length;
              const last = (len > 0) ? messageList.data[len - 1].content : '';
              this.chatMenu.push({
                username: (len > 0) ? messageList.data[len - 1].sender : '',
                message: last,
                date: (new Date(Date.now())).toLocaleTimeString(),
                id: String(chat.chatId),
                selected: this.chatMenu.length === 0,
                unread: 0
              });
            }
          })
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
        chatItem.unread++;
        chatItem.username = sender;
        chatItem.message = content;
      }
    }
  }

  renderChat(id: string) {
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
    this.myWebSocket.next({
      chatId: Number(this.currentChatID),
      sender: this.cookieService.get('username'),
      content: msg
    });
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
