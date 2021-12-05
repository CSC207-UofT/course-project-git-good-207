import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CreatePostComponent } from './create-post/create-post.component';
import { FeedComponent } from './feed/feed.component';
import { UserProfileViewComponent } from './user-profile-view/user-profile-view.component';

const routes: Routes = [
  { path: '', component: FeedComponent },
  { path: 'create-post', component: CreatePostComponent },
  { path: 'user-profile', component: UserProfileViewComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
