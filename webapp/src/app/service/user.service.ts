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

  public url: string;

  constructor(private http: HttpClient, private cookieService: CookieService) {
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

  public getAllUsers(): Observable<{
    data: User[];
    code: number;
  }> {
    return this.http.post<{
      data: User[];
      code: number;
    }>(`${this.url}/all`, {
      token: this.cookieService.get('token')
    });
  }

  public login(user: User): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.url}/signin`, {
      username: user.username,
      password: user.password,
    });
  }

  public getUserChats(): Observable<ChatList> {
    return this.http.post<ChatList>(`${this.url}/chats`, {
      id: Number(this.cookieService.get('id'))
    });
  }

  public getMessagesInChat(chatID: number): Observable<MessageList> {
    return this.http.post<MessageList>(`${this.url}/messages`, {
      chatId: chatID
    });
  }

  public addChatWithUser(id: string): Observable<{
    chatId: number;
    success: boolean;
    code: number;
  }> {
    return this.http.post<{
      chatId: number;
      success: boolean;
      code: number;
    }>(`${this.url}/addchat`, {
      participants: [id, this.cookieService.get('id')]
    });
  }

  public searchUsers(username: string): Observable<{
    data: User[];
    code: number;
  }> {
    return this.http.post<{
      data: User[];
      code: number;
    }>(`${this.url}/search`, {
      username: username,
      token: this.cookieService.get('token')
    });
  }

  public sendMesage(chatId: string, sender: string, content: string) {
    this.http.post<null>(`{${this.url}/app/send`, {
      chatId: chatId,
      seder: sender,
      content: content
    })
  }
}
