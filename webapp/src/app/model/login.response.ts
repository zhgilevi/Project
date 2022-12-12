export class LoginResponse {
    data?: {
        id: number;
        username: string;
        fName: string;
        lName: string;
        regDate: string;
    }
    // } = {
    //     id: -1,
    //     username: '',
    //     fName: '',
    //     lName: '',
    //     regDate: ''
    // };
    success?: boolean = false;
    code?: number;
    token?: string;
}