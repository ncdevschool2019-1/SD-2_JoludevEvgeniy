import {Component, OnInit, TemplateRef} from '@angular/core';
import {UserService} from '../../services/userService/user.service';
import {User} from '../models/user';
import {ModalService} from "../../services/modalService/modal.service";
import {BillingAccountService} from "../../services/billingAccountService/billingAccount.service";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {


  public users: User[];

  constructor(private userService: UserService, public modalService: ModalService,
              public billingAccountService: BillingAccountService) {
  }

  ngOnInit() {
    this.loadUsers();
  }

  private loadUsers(): void {
    this.userService.getUsers().subscribe(data => this.users = data);
  }

  public openModal(template: TemplateRef<any>, user: User): void {
    this.modalService.openModal(template);
    this.userService.selectedUser = User.cloneUser(user);
  }

  deleteUser(userId: number){
    this.userService.deleteUser(userId).subscribe(data => {
      this.loadUsers();
    })
  }

}
