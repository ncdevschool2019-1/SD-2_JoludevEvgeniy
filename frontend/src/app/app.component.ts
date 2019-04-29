import {Component, Input, OnInit} from '@angular/core';
import {User} from './modules/models/user';
import {AuthorizationService} from './services/authorization.service';
import {LocalizationService} from './services/localization.service';
import {TokenStorageService} from './services/token-storage.service';
import {UserService} from './services/user.service';
import {Subscription} from 'rxjs';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  subscriptions: Subscription[] = [];

  constructor(private tokenService: TokenStorageService, private authService: AuthorizationService,
              private userService: UserService) {
  }

  ngOnInit() {
    if(this.tokenService.getToken()){
      this.subscriptions.push(this.userService.getLoggedUser(this.tokenService.getLogin()).subscribe((data => {
        this.authService.setAuthUser(data);
      })))
    }
  }


}
