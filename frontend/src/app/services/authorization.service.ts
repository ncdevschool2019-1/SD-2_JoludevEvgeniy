import {Injectable} from '@angular/core';
import {User} from '../modules/models/user';
import {Subscript} from '../modules/models/subscript';

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


  public outFromAccount() {
    this.authorizedUser = new User();
  }

  public isRole(): boolean {
    if(typeof this.authorizedUser.role === 'undefined'){
      return false;
    }
    return true;
  }

  public checkSubscript(subscript: Subscript): boolean {
    return this.authorizedUser.billingAccounts.some((billingAccount) =>
      billingAccount.activeSubscripts.some((activeSubscript) => activeSubscript.subscript.id == subscript.id));
  }
}
