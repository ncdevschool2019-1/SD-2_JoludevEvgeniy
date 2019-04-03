import {Component, OnInit} from '@angular/core';
import {AuthorizationService} from '../../services/authorizationService/authorization.service';

import {ModalService} from '../../services/modalService/modal.service';
import {User} from '../models/user';
import {UserService} from '../../services/userService/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  public newLogin: string;
  public newEmail: string;
  public oldPassword: string;
  public newPassword: string;
  updatebleUser: User = User.cloneUser(this.authService.authorizedUser);


  constructor(private authService: AuthorizationService, private modalService: ModalService,
              private userService: UserService) {
  }


  ngOnInit() {
  }

  changeLogin() {
    this.updatebleUser.login = this.newLogin;
    this.userService.updateUsersLogin(this.updatebleUser).subscribe(data => {
      this.authService.updateAuthorization();
      this.newLogin = '';
    });
  }

  changePassword() {
    this.updatebleUser.password = this.newPassword;
    this.userService.updateUsersPassword(this.updatebleUser).subscribe(data => {
      this.authService.updateAuthorization();
      this.newPassword = '';
      this.oldPassword = '';
    });
  }

  changeEmail() {
    this.updatebleUser.email = this.newEmail;
    this.userService.updateUsersEmail(this.updatebleUser).subscribe(data => {
      this.authService.updateAuthorization();
      this.newEmail = '';
    });
  }


}
