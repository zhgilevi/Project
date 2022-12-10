import { User } from './user'

export class CustomResponse {
    code: number = 0;
    message: string = "";
    responseList: User[] = [];
}