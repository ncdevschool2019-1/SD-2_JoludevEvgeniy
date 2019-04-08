import {Injectable} from "@angular/core";
import {BillingAccount} from "../modules/models/billing-account";
import {Subscript} from '../modules/models/subscript';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BillingAccountService {
  private path: string = '/api/billing-accounts';

  constructor(private http: HttpClient){

  }

  public saveBillingAccount(billingAccount: BillingAccount): Observable<BillingAccount>{
    return this.http.post<BillingAccount>(this.path, billingAccount);
  }

  public getBillingAccountById(billingAccountId: number): Observable<BillingAccount>{
    return this.http.get<BillingAccount>(this.path + '/' + billingAccountId);
  }

  public deleteBillingAccount(billingAccountId: number): Observable<void>{
    return this.http.delete<void>(this.path + '/' + billingAccountId);
  }

  public isSelectedBAIdUndefined(billingAccount: BillingAccount): boolean {
    return typeof billingAccount.id === 'undefined';
  }

  public getSelectedBASubscriptsLength(billingAccount: BillingAccount): string {
    if (!billingAccount.activeSubscripts) {
      return "0";
    } else {
      return billingAccount.activeSubscripts.length.toString();
    }
  }

  public isBalanceMoreThanSubscribePrice(subscript: Subscript, billingAccount: BillingAccount): boolean{
    if (subscript.price > billingAccount.balance){
      return false;
    }
    return true;
  }

}
