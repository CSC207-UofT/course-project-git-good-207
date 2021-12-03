import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { map, Observable, startWith } from 'rxjs';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
  myControl = new FormControl();
  users: string[] = ['Eric', 'Shawn', 'Yolanda'];
  filteredUsers: Observable<string[]>;

  constructor() { 
    this.filteredUsers = this.myControl.valueChanges.pipe(
      startWith(''),
      map(value => this._filter(value)),
    );
  }

  ngOnInit(): void {}

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();

    return this.users.filter(user => user.toLowerCase().includes(filterValue));
  }
}
