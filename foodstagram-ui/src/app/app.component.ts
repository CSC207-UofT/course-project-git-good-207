import { Component, OnInit } from '@angular/core';
import { LoginService } from './login.service';
import { User } from './login/login.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'Foodstagram';
  public user: User | null = null;

  constructor(private _loginService: LoginService) {
    this._loginService.currentUser$.subscribe((user) => {
      this.user = user;
    });
  }

  ngOnInit() {

  }
}
