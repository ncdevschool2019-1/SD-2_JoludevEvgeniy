import {Injectable} from "@angular/core";
import {BillingAccount} from "../../modules/models/billing-account";
import {Subscript} from '../../modules/models/subscript';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BillingAccountService {
  private _selectedBillingAccount: BillingAccount = new BillingAccount();
  private path: string = '/api/billing-accounts';

  constructor(private http: HttpClient){

  }

  get selectedBillingAccount(): BillingAccount {
    return this._selectedBillingAccount;
  }

  set selectedBillingAccount(value: BillingAccount) {
    this._selectedBillingAccount = value;
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

  public clearSelectedBillingAccount(): void {
    this.selectedBillingAccount = new BillingAccount();
  }

  public isSelectedBAIdUndefined(): boolean {
    return typeof this.selectedBillingAccount.id === 'undefined';
  }

  public getSelectedBASubscriptsLength(): string {
    if (typeof this.selectedBillingAccount.activeSubscripts === 'undefined') {
      return "";
    } else {
      return this.selectedBillingAccount.activeSubscripts.length.toString();
    }
  }

  public getBAStatus(billingAccount: BillingAccount): string {
    return billingAccount.active ? "Активно" : "Заблокировано"
  }

  public isBalanceMoreThanSubscribePrice(subscript: Subscript): boolean{
    if (subscript.price > this.selectedBillingAccount.balance){
      return false;
    }
    return true;
  }
}
