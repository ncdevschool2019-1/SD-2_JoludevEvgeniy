
import {Component, EventEmitter, OnDestroy, OnInit, Output} from '@angular/core';
import {ModalService} from '../../../services/modal.service';
import {User} from '../../models/user';
import {UserService} from '../../../services/user.service';
import {AuthorizationService} from '../../../services/authorization.service';
import {ToastrService} from 'ngx-toastr';
import {Subscription} from 'rxjs';
import {Ng4LoadingSpinnerService} from 'ng4-loading-spinner';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit, OnDestroy {

  private user: User = new User;
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
    this.user = new User;
  }

  createUser(event) {
    this.loadingService.show();
    this.subscriptions.push(this.userService.saveUser(this.user).subscribe(data => {
      this.authService.setAuthUser(data);
      this.closeModal();
      this.toastr.success('Account created successfully!', data.login);
    }, error => {
      event.target.disabled = false;
      this.toastr.error(error.error, 'Error');
    }, () => this.loadingService.hide()));
  }

}
