import { Component, OnInit } from '@angular/core';
import { LoginService } from '../login.service';

export interface User {
  username: string,
  password: string
}

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  constructor(private _loginService: LoginService) { }

  ngOnInit(): void {
  }

  login(): void {
    this._loginService.login('Eric', '1234');
  }

}
