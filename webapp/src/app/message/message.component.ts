import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-message',
  template: `
    <div [ngClass]="getFullClass()">
      <div class="flex-shrink-1 bg-light rounded py-2 px-3 ml-3">
        <div class="font-weight-bold mb-1">{{ sender }}</div>
        {{ content }}
      </div>
    </div>
  `,
  styleUrls: ['./message.component.css'],
})
export class MessageComponent {
  @Input() sender?: string;
  @Input() content?: string;

  getFullClass(): Object {
    const isRight = this.sender === 'You';
    return {
      'pb-4': true,
      'chat-message-left': !isRight,
      'chat-message-right': isRight,
    };
  }
}
