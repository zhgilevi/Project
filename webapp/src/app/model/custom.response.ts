import { User } from './user'

export class CustomResponse {
    code: number = 0;
    token: string = '';
    message: string = "";
    responseList: any[] = [];
}