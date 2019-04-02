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

  static cloneUser(user: User): User {
    const clonedUser: User = new User();
    clonedUser.id = user.id;
    clonedUser.login = user.login;
    clonedUser.password = user.password;
    clonedUser.email = user.email;
    clonedUser.role = user.role;
    clonedUser.roleId = user.roleId;
    clonedUser.billingAccounts = user.billingAccounts;
    clonedUser.imagePath = user.imagePath;
    return clonedUser;
  }
}
