import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-chat-item',
  template: `
    <div [ngClass]="getFullClass()" (click)="handleClick()">
        <div class="d-flex w-100 align-items-center justify-content-between">
          <strong class="mb-1">{{ dialogWith }}</strong>
          <small>{{ date }}</small>
        </div>
        <div class="col-10 mb-1 small" *ngIf="username.length !== 0"><strong>{{ getUnread() }}{{ username }}: </strong>{{ getMessageSlice() }}</div>
</div>
  `,
  styles: [
  ]
})
export class ChatItemComponent {
  @Input() username: string = '';
  @Input() message: string = '';
  @Input() date: string = '';
  @Input() selected: boolean = false;
  @Input() id: string = '';
  @Input() unread: number = 0;
  @Input() dialogWith: string = '';

  @Output() selectRequest = new EventEmitter<string>();

  ngOnInit() {
    console.log("Created Chat Item with ID", this.id, "Selected:", this.selected);
  }

  getFullClass(): Object {
    return {
      "aria-current": this.selected,
      "list-group-item list-group-item-action py-3 lh-tight": true,
      "active": this.selected
    }
  }

  getUnread(): string {
    return (this.unread != 0) ? `(${this.unread}) ` : '';
  }

  getMessageSlice(): string {
    const excess = `(${this.getUnread()}) ${this.username}:`.length;
    const len = this.message.length;
    return this.message.slice(0, Math.min((25 - excess < 0) ? 0 : 25 - excess, len)) + '..';
  }

  handleClick() {
    this.selectRequest.emit(this.id);
  }
}
