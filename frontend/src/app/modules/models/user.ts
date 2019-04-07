import {BillingAccount} from './billing-account';


export class User {
  id: number;
  login: string;
  password: string;
  email: string;
  role: string;
  roleId: number;
  billingAccounts: BillingAccount[];
  imagePath: string;

}
