import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {ModalService} from '../../services/modal.service';
import {AuthorizationService} from '../../services/authorization.service';
import {User} from '../models/user';


@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  authorizedUser: User = new User();

  constructor(private modalService: ModalService, private authService: AuthorizationService) {
  }

  getAuthUser() {
    this.authorizedUser = this.authService.authorizedUser;
  }

  onChanged(){
    this.getAuthUser();
  }

  ngOnInit() {
    this.getAuthUser();
  }

  outFromAccount() {
    this.authService.outFromAccount();
    this.getAuthUser();
  }

  isRole(): boolean {
    return this.authService.isRole();
  }

}
