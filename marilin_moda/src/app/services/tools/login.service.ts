import { Injectable } from '@angular/core';
import { ApiLogin } from '../apis/api.login.service';

@Injectable()
export class LoginService {
  constructor(private apiLogin: ApiLogin) {}

  login(obj: any) {
    return this.apiLogin.login(obj);
  }
}
