import {Component, EventEmitter, OnDestroy, OnInit, Output} from '@angular/core';
import {ModalService} from '../../../services/modal.service';
import {AuthorizationService} from '../../../services/authorization.service';
import {UserService} from '../../../services/user.service';
import {ToastrService} from 'ngx-toastr';
import {User} from '../../models/user';
import {Subscription} from 'rxjs';
import {Ng4LoadingSpinnerService} from 'ng4-loading-spinner';

@Component({
  selector: 'app-authorization',
  templateUrl: './authorization.component.html',
  styleUrls: ['./authorization.component.css']
})
export class AuthorizationComponent implements OnInit, OnDestroy {


  inputUser: User = new User();
  private subscriptions: Subscription[] = [];

  constructor(private modalService: ModalService, private authService: AuthorizationService,
              private userService: UserService, private toastr: ToastrService,
              private loadingService: Ng4LoadingSpinnerService) {
  }

  ngOnInit() {
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(value => value.unsubscribe());
  }

  authorization(event) {
    this.loadingService.show();
    this.subscriptions.push(this.userService.getLoginUser(this.inputUser).subscribe(data => {
      this.authService.setAuthUser(data);
      this.closeModal();
      this.toastr.success('You have entered successfully!', data.login);
    }, error => {
      event.target.disabled = false;
      this.toastr.error(error.error, 'Error');
    }, () => this.loadingService.hide()));

  }

  closeModal() {
    this.modalService.closeModal();
    this.inputUser = new User();
  }

}
