import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.scss']
})
export class CreatePostComponent implements OnInit {

  constructor(private _router: Router) { }

  ngOnInit(): void {
  }

  submitPost(): void {
    this._router.navigate(['']);
  }

}
