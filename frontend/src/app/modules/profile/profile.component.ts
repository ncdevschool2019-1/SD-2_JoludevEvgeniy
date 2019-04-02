import {Component, OnInit} from '@angular/core';
import {AuthorizationService} from "../../services/authorizationService/authorization.service";

import {ModalService} from "../../services/modalService/modal.service";
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



  constructor(private authService: AuthorizationService, private modalService: ModalService,
              private userService: UserService) {
  }


  ngOnInit() {
  }

  changeLogin(){
    let user = User.cloneUser(this.authService.authorizedUser);
    user.login = this.newLogin;
    this.userService.saveUser(user).subscribe(data => {
      this.authService.updateAuthorization();
    });
  }

  changePassword(){
    let user = User.cloneUser(this.authService.authorizedUser);
    user.password = this.newPassword;
    this.userService.saveUser(user).subscribe(data => {
      this.authService.updateAuthorization();
    });
  }

  changeEmail(){
    let user = User.cloneUser(this.authService.authorizedUser);
    user.email = this.newEmail;
    this.userService.saveUser(user).subscribe(data => {
      this.authService.updateAuthorization();
    });
  }


}
