import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { map, Observable, startWith } from 'rxjs';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
  userSearch = new FormControl();
  users: string[] = ['Eric', 'Shawn', 'Yolanda'];
  filteredUsers: Observable<string[]>;

  constructor(private _router: Router) { 
    this.filteredUsers = this.userSearch.valueChanges.pipe(
      startWith(''),
      map(value => this._filter(value)),
    );
  }

  ngOnInit(): void {
  }

  public openUserProfile(): void {
    this._router.navigate(['/user-profile']);
  }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.users.filter(user => user.toLowerCase().includes(filterValue));
  }
}
