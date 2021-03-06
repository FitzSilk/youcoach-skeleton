import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from './authentication/authentication.service';
import {TranslateService} from '@ngx-translate/core';
import {UserService} from './services/user.service';
import {User} from './classes/user';
import {Observable} from 'rxjs';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  username;
  language = 'en';
  id;

  constructor(private authenticationService: AuthenticationService, private translate: TranslateService, private userService: UserService) {
  }

  ngOnInit(): void {
  }

  switchLanguage(language: string) {
    this.translate.use(language);
    this.language = language;
  }

  currentLanguage() {
    return this.language;
  }

}
