import {Component, EventEmitter, OnDestroy, OnInit, Output} from '@angular/core';
import {ModalService} from '../../services/modal.service';
import {AuthorizationService} from '../../services/authorization.service';
import {User} from '../models/user';
import {Subscription} from 'rxjs';
import {TokenStorageService} from '../../services/token-storage.service';


@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit, OnDestroy {


  authorizedUser: User = new User();

  subscriptions: Subscription[] = [];

  constructor(private modalService: ModalService, private authService: AuthorizationService,
              private tokenService: TokenStorageService) {
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

  ngOnInit() {
    this.getAuthUser();
  }

  outFromAccount() {
    this.tokenService.signOut();
    this.authService.outFromAccount();
    this.getAuthUser();
  }

  isRole(): boolean {
    return this.authService.isRole();
  }

}
