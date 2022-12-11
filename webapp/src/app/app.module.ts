import { BrowserModule } from '@angular/platform-browser';
import { NgModule, OnInit } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { UserListComponent } from './user-list/user-list.component';
import { UserService } from './service/user.service';
import { HomepageComponent } from './homepage/homepage.component';
import { ChatItemComponent } from './chat-item/chat-item.component';
import { MessageComponent } from './message/message.component';
import { SignInComponent } from './sign-in/sign-in.component';
import { SignUpComponent } from './sign-up/sign-up.component';
import { CookieService } from 'ngx-cookie-service';

@NgModule({
  declarations: [
    AppComponent,
    UserListComponent,
    HomepageComponent,
    ChatItemComponent,
    MessageComponent,
    SignInComponent,
    SignUpComponent,
  ],
  imports: [BrowserModule, AppRoutingModule, HttpClientModule, FormsModule],
  providers: [UserService, CookieService],
  bootstrap: [AppComponent],
})
export class AppModule {

  constructor(private cookieService:CookieService){}

  ngOnInit() {
    // default values
    this.cookieService.set('token','');
    this.cookieService.set('username', '');
    this.cookieService.set('fname', '');
    this.cookieService.set('lname', '');
    this.cookieService.set('id', '');
    this.cookieService.set('regDate', '');
  }
}
