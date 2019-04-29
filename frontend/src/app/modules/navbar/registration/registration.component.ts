
import {Component, EventEmitter, OnDestroy, OnInit, Output} from '@angular/core';
import {ModalService} from '../../../services/modal.service';
import {User} from '../../models/user';
import {UserService} from '../../../services/user.service';
import {AuthorizationService} from '../../../services/authorization.service';
import {ToastrService} from 'ngx-toastr';
import {Subscription} from 'rxjs';
import {Ng4LoadingSpinnerService} from 'ng4-loading-spinner';
import {AuthRegUser} from '../../models/auth-reg-user';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit, OnDestroy {

  private user: AuthRegUser = new AuthRegUser();
  private subscriptions: Subscription[] = [];

  constructor(private modalService: ModalService, private userService: UserService,
              private authService: AuthorizationService, private toastr: ToastrService,
              private loadingService: Ng4LoadingSpinnerService) {
  }

  ngOnInit() {
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(value => value.unsubscribe());
  }


  closeModal() {
    this.modalService.closeModal();
    this.user = new AuthRegUser();
  }

  createUser(event) {
    this.loadingService.show();
    this.subscriptions.push(this.userService.saveUser(this.user).subscribe(data => {
      this.closeModal();
      this.toastr.success('Account created successfully! Now you should entrance to account', data.login);
    }, error => {
      event.target.disabled = false;
      this.toastr.error(error.error, 'Error');
      this.loadingService.hide();
    }, () => this.loadingService.hide()));
  }

}
