import {Subscript} from './subscript';
import {ActiveSubscript} from './active-subscript';

export class BillingAccount {
  id: number;
  name: string;
  balance: number;
  userId: number;
  activeSubscripts: ActiveSubscript[];
  active: boolean;

  static cloneBillingAccount(billingAccount: BillingAccount): BillingAccount {
    const clonedBillingAccount: BillingAccount = new BillingAccount();
    clonedBillingAccount.id = billingAccount.id;
    clonedBillingAccount.name = billingAccount.name;
    clonedBillingAccount.balance = billingAccount.balance;
    clonedBillingAccount.userId = billingAccount.userId;
    clonedBillingAccount.activeSubscripts = billingAccount.activeSubscripts;
    clonedBillingAccount.active = billingAccount.active;
    return clonedBillingAccount;
  }
}
