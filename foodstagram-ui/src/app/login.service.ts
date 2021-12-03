import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { User } from './login/login.component';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  public currentUserSubject: Subject<User | null> = new BehaviorSubject<User | null>(null);
  public currentUser$: Observable<User | null> = this.currentUserSubject.asObservable();

  constructor() { }

  login(username: string, password: string) {
    this.currentUserSubject.next({
      username: username,
      password: password
    });
  }

  logout() {
    this.currentUserSubject.next(null);
  }
}
