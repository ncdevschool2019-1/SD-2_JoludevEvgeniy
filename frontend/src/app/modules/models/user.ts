import {BillingAccount} from './billing-account';


export class User {
  id: number;
  login: string;
  email: string;
  role: string;
  imagePath: string;
  billingAccounts: BillingAccount[];

}
