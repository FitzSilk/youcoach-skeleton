import {Component, OnInit} from '@angular/core';
import {User} from '../classes/user';
import {UserService} from '../services/user.service';
import {AuthenticationService} from '../authentication/authentication.service';
import {filter, map} from "rxjs/operators";

@Component({
  selector: 'app-coaches-overview',
  templateUrl: './coaches-overview.component.html',
  styleUrls: ['./coaches-overview.component.css']
})
export class CoachesOverviewComponent implements OnInit {

  users: User[];
  user: User;

  constructor(private userService: UserService, private authenticationService: AuthenticationService) {
  }

  ngOnInit(): void {
    this.loadUser();
    this.getCoaches();
  }

  loadUser(): void {
    const id: string = this.authenticationService.getId();
    this.userService.getUserById(id).subscribe(user => this.user = user);
  }

  getCoaches(): void {
    this.userService.getCoaches().pipe(
      map(userList => userList.filter(user => user.id !== this.authenticationService.getId()))
    ).subscribe(users => this.users = users);
  }

}
