import {Injectable} from '@angular/core';
import {User} from '../../modules/models/user';
import {UserService} from '../userService/user.service';
import {Subscript} from '../../modules/models/subscript';

@Injectable({
  providedIn: 'root'
})
export class AuthorizationService {

  get authorizedUser(): User {
    return this._authorizedUser;
  }

  set authorizedUser(value: User) {
    this._authorizedUser = value;
  }

  private _authorizedUser: User = new User();

  constructor(private userService: UserService) {
  }

  public updateAuthorization(): void {
    this.userService.getUserById(this.authorizedUser.id).subscribe(data => {
      this.authorizedUser = data;
    })
  }

  public outFromAccount(){
    this.authorizedUser = new User;
  }

  public isRole(): boolean {
    if (typeof this.authorizedUser.role === 'undefined') {
      return false;
    }
    return true;
  }

  public checkSubscript(subscript: Subscript): boolean {
    for (let billingAccount of this.authorizedUser.billingAccounts) {
      for (let activeSubscript of billingAccount.activeSubscripts) {
        if (activeSubscript.subscript.id == subscript.id) {
          return true;
        }
      }
    }
    return false;
  }
}
