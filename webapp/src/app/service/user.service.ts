import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../model/user';
import { ValidResponse } from '../model/valid.response';
import { Observable } from 'rxjs';
import { LoginResponse } from '../model/login.response';
import { ChatList } from '../model/chats.list';
import { CookieService } from 'ngx-cookie-service';
import { MessageList } from '../model/message.list';

@Injectable()
export class UserService {
  private url: string;

  constructor(private http: HttpClient, private cookieServics: CookieService) {
    this.url = 'http://localhost:8080/api';
  }

  public register(user: User): Observable<ValidResponse> {
    return this.http.post<{
      success: boolean;
      code: number;
    }>(`${this.url}/signup`, {
      username: user.username,
      fname: user.fname,
      lname: user.lname,
      password: user.password,
    });
  }

  public login(user: User): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.url}/signin`, {
      username: user.username,
      password: user.password,
    });
  }

  public validateToken(): Observable<ValidResponse> {
    return this.http.post<ValidResponse>(`${this.url}/token`, {
      token: this.cookieServics.get('token')
    });
  }

  public getUserChats(): Observable<ChatList> {
    return this.http.post<ChatList>(`${this.url}/chats`, {
      id: Number(this.cookieServics.get('id'))
    });
  }

  public getMessagesInChat(chatID: number): Observable<MessageList> {
    return this.http.post<MessageList>(`${this.url}/messages`, {
      chatId: chatID
    });
  }

  public addChatWithUser(username: string): Observable<{
    chatId: number;
    success: boolean;
    code: number;
  }> {
    return this.http.post<{
      chatId: number;
      success: boolean;
      code: number;
    }>(`${this.url}/addChat`, {
      chatUsers: [username, this.cookieServics.get('username')]
    });
  }
}
