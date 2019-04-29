import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthorizationService} from '../../services/authorization.service';

import {ModalService} from '../../services/modal.service';
import {User} from '../models/user';
import {UserService} from '../../services/user.service';
import {ToastrService} from 'ngx-toastr';
import {Subscription} from 'rxjs';
import {ChangeParamsUser} from '../models/change-params-user';
import {Ng4LoadingSpinnerService} from 'ng4-loading-spinner';
import {TokenStorageService} from '../../services/token-storage.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit, OnDestroy {

  public changeUser: ChangeParamsUser = new ChangeParamsUser();
  authorizedUser: User = new User();
  private subscriptions: Subscription[] = [];
  fileList: FileList;


  constructor(private authService: AuthorizationService, private modalService: ModalService,
              private userService: UserService, private toastr: ToastrService,
              private loadingService: Ng4LoadingSpinnerService, private tokenService: TokenStorageService) {
  }


  ngOnInit() {
    this.getAuthUser();
    this.changeUser.userId = this.authorizedUser.id;
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(value => value.unsubscribe());
  }

  getAuthUser() {
    this.subscriptions.push(this.authService.subscribeToAuthUser().subscribe(value => {
      this.authorizedUser = value;
    }));
    this.authService.getAuthUser();
  }

  uploadImage(event) {
    this.loadingService.show();
    this.fileList = event.target.files;
    if (this.fileList && this.fileList.length > 0) {

      this.subscriptions.push(this.userService.saveUsersImage(this.fileList[0], this.authorizedUser.id).subscribe(value => {
        this.authorizedUser = value;
        this.authService.setAuthUser(this.authorizedUser);
        this.toastr.success('Image has been saved', 'Success');
      }, error => {
        this.toastr.error('Image download failed', 'Error');
        this.loadingService.hide();
      }, () => this.loadingService.hide()));
    }
  }


  changeLogin() {
    this.loadingService.show();
    this.subscriptions.push(this.userService.updateUsersLogin(this.changeUser).subscribe(data => {
      this.tokenService.saveToken(data.token);
      this.tokenService.saveLogin(data.login);
      this.changeUser.newLogin = '';
      this.changeUser.oldPassword = '';
      this.toastr.success('Your login has been changed!', 'Success');
    }, error => {
      this.toastr.error(error.error, 'Error');
      this.loadingService.hide();
    }, () => {
      this.subscriptions.push(this.userService.getLoggedUser(this.tokenService.getLogin()).subscribe(data => {
        this.authorizedUser = data;
        this.authService.setAuthUser(this.authorizedUser);
        this.loadingService.hide();
      }));
    }));
  }

  changePassword() {
    this.loadingService.show();
    this.subscriptions.push(this.userService.updateUsersPassword(this.changeUser).subscribe(data => {
      this.tokenService.saveToken(data.token);
      this.changeUser.newPassword = '';
      this.changeUser.oldPassword = '';
      this.toastr.success('Your password has been changed!', 'Success');
    }, error => {
      this.toastr.error(error.error, 'Error');
      this.loadingService.hide();
    }, () => {
      this.subscriptions.push(this.userService.getLoggedUser(this.tokenService.getLogin()).subscribe(data => {
        this.authorizedUser = data;
        this.authService.setAuthUser(this.authorizedUser);
        this.loadingService.hide();
      }));
    }));


  }

  changeEmail() {
    this.loadingService.show();
    this.subscriptions.push(this.userService.updateUsersEmail(this.changeUser).subscribe(data => {
      this.tokenService.saveToken(data.token);
      this.changeUser.newEmail = '';
      this.changeUser.oldPassword = '';
      this.toastr.success('Your E-mail has been changed', 'Success');
    }, error => {
      this.toastr.error(error.error, 'Error');
      this.loadingService.hide();
    }, () => {
      this.subscriptions.push(this.userService.getLoggedUser(this.tokenService.getLogin()).subscribe(data => {
        this.authorizedUser = data;
        this.authService.setAuthUser(this.authorizedUser);
        this.loadingService.hide();
      }));
    }));


  }


}
