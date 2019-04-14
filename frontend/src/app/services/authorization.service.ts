import {Injectable} from '@angular/core';
import {User} from '../modules/models/user';
import {Subscript} from '../modules/models/subscript';
import {Observable, Subject} from 'rxjs';
import {filter} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthorizationService {

  private authorizedUser: User = new User();

  private subject = new Subject<User>();

  public setAuthUser(user: User) {
    this.authorizedUser = user;
    this.getAuthUser();
  }

  public getAuthUser() {
    this.subject.next(this.authorizedUser);
  }

  public subscribeToAuthUser(): Observable<User> {
    return this.subject.asObservable();
  }

  public outFromAccount() {
    this.authorizedUser = new User();
    this.subject.next(this.authorizedUser);
  }

  public isRole(): boolean {
    if (typeof this.authorizedUser.role === 'undefined') {
      return false;
    }
    return true;
  }

  public checkSubscript(subscript: Subscript): boolean {
    return this.authorizedUser.billingAccounts.some((billingAccount) =>
      billingAccount.activeSubscripts.some((activeSubscript) => activeSubscript.subscript.id == subscript.id));

  }
}
