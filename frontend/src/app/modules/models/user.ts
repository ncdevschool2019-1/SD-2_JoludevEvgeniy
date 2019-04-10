import {BillingAccount} from './billing-account';


export class User {
  id: number;
  login: string;
  password: string;
  email: string;
  role: string;
  imagePath: string;
  billingAccounts: BillingAccount[];

  static cloneUser(user: User): User{
    let clonedUser: User = new User();
    clonedUser.id = user.id;
    clonedUser.login = user.login;
    clonedUser.password = user.password;
    clonedUser.role = user.role;
    clonedUser.imagePath = user.imagePath;
    clonedUser.billingAccounts = user.billingAccounts;
    return clonedUser;
  }
}
