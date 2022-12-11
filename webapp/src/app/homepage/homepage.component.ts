import { Component, ViewChild, ElementRef, OnInit } from '@angular/core';
import { UserService } from '../service/user.service';
import { CookieService } from 'ngx-cookie-service';
import { CookieValidation } from '../model/token.validation';
import { CustomResponse } from '../model/custom.response';
import { User } from '../model/user';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css'],
})
export class HomepageComponent {
  currentChatID: string;

  chatList: {
    [key: string]: {
      chat: {
        sender: string;
        content: string;
        date: Date;
      }[];
      user: User;
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

  tokenIsValid: boolean = false;

  @ViewChild('scrollable') private myScrollContainer: ElementRef =
    new ElementRef<any>('scrollable');

  constructor(private userService: UserService, private cookieService: CookieService) {
    this.currentChatID = '';
    this.chatList = {};
  }

  validateToken() {
    this.userService.tokenValidation().subscribe((res: CookieValidation) => {
      this.tokenIsValid = res.valid;
    });
  }

  ngOnInit() {
    let users: User[] = [];
    this.validateToken();
    console.log("Token Valid:", this.tokenIsValid);
    this.userService.findAll().subscribe((data: CustomResponse) => {
      users = data.responseList;
      console.log('Got Response:', users);
      let flag = true;
      for (const user of users) {
        if (flag) {
          this.currentChatID = user.id;
          flag = false;
        }
        const msgList = this.getMessageList(user.username);
        this.chatList[user.id] = {
          chat: msgList,
          user: user,
        };
        const len = this.chatList[user.id].chat.length;
        const msg =
          len > 0
            ? this.chatList[user.id].chat[len - 1].content.slice(
                0,
                Math.min(len, 15)
              ) + '..'
            : '';
        this.chatMenu.push({
          username: user.username,
          message: msg,
          date:
            len > 0
              ? this.chatList[user.id].chat[len - 1].date.toLocaleTimeString()
              : '',
          id: user.id,
          selected: this.currentChatID == user.id,
          unread: 0
        });
      }
      console.log('Selected ID:', this.currentChatID);
      console.log('Generated ChatList:', this.chatList);
    });
    setInterval(() => {
      const id =
        this.chatMenu[Math.floor(Math.random() * this.chatMenu.length)].id;
      const msg = this.generateMessage(this.chatList[id].user.username);
      this.chatList[id].chat.push(msg);
      for (const chatItem of this.chatMenu) {
        if (chatItem.id == id) {
          const len = msg.content.length;
          chatItem.message = msg.content.slice(0, Math.min(len, 15)) + '..';
          chatItem.date = msg.date.toLocaleTimeString();
          if (this.currentChatID != id) chatItem.unread++;
        }
      }
    }, 200);
  }

  renderChat(id: string) {
    this.currentChatID = id;
    for (const chatItem of this.chatMenu) {
      chatItem.selected = chatItem.id == id;
      if (chatItem.id == id) {
        chatItem.unread = 0;
      }
    }
    this.scrollToBottom();
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

  generateMessageText(): string {
    const availableSentences = [
      'Lorem ipsum dolor sit amet, consectetur adipiscing elit.',
      'Nullam bibendum urna in mauris dapibus finibus.',
      'Donec vestibulum, justo a aliquet rutrum, augue nulla lobortis tellus, vitae suscipit risus lectus sit amet elit.',
      'Sed consectetur risus bibendum ex venenatis, vitae tincidunt orci consequat. ',
      'Integer mi metus, semper facilisis justo vitae, imperdiet tincidunt sem. Nulla maximus metus orci, at hendrerit ligula euismod at.',
      'Pellentesque luctus sapien id urna lobortis, ut tempor velit efficitur.',
      'Praesent vel interdum purus.',
      'Sed erat leo, cursus quis sem ut, ornare rutrum magna.',
    ];
    return availableSentences.filter(() => Math.random() < 0.5).join(' ');
  }

  generateMessage(username: string): {
    sender: string;
    content: string;
    date: Date;
  } {
    return {
      sender: Math.random() < 0.5 ? 'You' : username,
      content: this.generateMessageText(),
      date: new Date(Date.now()),
    };
  }

  getMessageList(username: string): {
    sender: string;
    content: string;
    date: Date;
  }[] {
    return Array(Math.floor(Math.random() * 100) + 1)
      .fill(null)
      .map(() => {
        return this.generateMessage(username);
      });
  }
}
