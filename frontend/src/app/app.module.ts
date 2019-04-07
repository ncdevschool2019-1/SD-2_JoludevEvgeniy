import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {BsDropdownModule} from 'ngx-bootstrap/dropdown';
import {TooltipModule} from 'ngx-bootstrap/tooltip';
import {ModalModule} from 'ngx-bootstrap/modal';
import {FormsModule} from '@angular/forms';

import {AppComponent} from './app.component';
import {SubscriptComponent} from './modules/subscript/subscript.component';

import {HttpClientModule} from '@angular/common/http';
import {UserComponent} from './modules/user/user.component';
import {AuthorizationComponent} from './modules/navbar/authorization/authorization.component';
import {NavbarComponent} from './modules/navbar/navbar.component';
import {RouterModule, Routes} from '@angular/router';
import {JumbotronComponent} from './modules/jumbotron/jumbotron.component';
import {ProfileComponent} from './modules/profile/profile.component';
import {PaginationModule, TabsModule} from 'ngx-bootstrap';
import {BillingAccountComponent} from './modules/billingAccount/billing-account.component';
import {RegistrationComponent} from './modules/navbar/registration/registration.component';
import {AddSubscriptComponent} from './modules/profile/add-subscript/add-subscript.component';
import {JumbotronPageComponent} from './modules/layout/components/jumbotronPage/jumbotron-page.component';
import {SubscriptPageComponent} from './modules/layout/components/subscriptPage/subscript-page.component';
import {BillingAccountPageComponent} from './modules/layout/components/billingAccountPage/billing-account-page.component';
import {UserPageComponent} from './modules/layout/components/userPage/user-page.component';
import {ProfilePageComponent} from './modules/layout/components/profilePage/profile-page.component';
import {ToSubscriptComponent} from './modules/subscript/components/toSubscript/to-subscript.component';
import {EditSubscriptComponent} from './modules/subscript/components/editSubscript/edit-subscript.component';
import {SubscriptsTableComponent} from './modules/billingAccount/components/subscriptsTable/subscripts-table.component';
import {ReplenishBalanceComponent} from './modules/billingAccount/components/replenishBalance/replenish-balance.component';
import {UsersBillingAccountsComponent} from './modules/user/components/usersBillingAccounts/users-billing-accounts.component';
import {AddBillingAccountComponent} from './modules/billingAccount/components/addBillingAccount/add-billing-account.component';
import {ToastrModule} from 'ngx-toastr';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {AuthorizationService} from './services/authorizationService/authorization.service';

const routes: Routes = [
  {path: '', redirectTo: 'jumbotron', pathMatch: 'full'},
  {path: 'jumbotron', component: JumbotronPageComponent},
  {path: 'subscripts', component: SubscriptPageComponent},
  {path: 'users', component: UserPageComponent},
  {path: 'profile', component: ProfilePageComponent},
  {path: 'billing-accounts', component: BillingAccountPageComponent}
];

@NgModule({
  declarations: [
    AppComponent,
    SubscriptComponent,
    UserComponent,
    AuthorizationComponent,
    NavbarComponent,
    JumbotronComponent,
    ProfileComponent,
    BillingAccountComponent,
    RegistrationComponent,
    AddSubscriptComponent,
    JumbotronPageComponent,
    SubscriptPageComponent,
    BillingAccountPageComponent,
    UserPageComponent,
    ProfilePageComponent,
    ToSubscriptComponent,
    EditSubscriptComponent,
    SubscriptsTableComponent,
    ReplenishBalanceComponent,
    UsersBillingAccountsComponent,
    AddBillingAccountComponent
  ],

  exports: [RouterModule],

  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    BsDropdownModule.forRoot(),
    TooltipModule.forRoot(),
    ModalModule.forRoot(),
    RouterModule.forRoot(routes),
    TabsModule.forRoot(),
    PaginationModule.forRoot(),
    ToastrModule.forRoot({
      timeOut: 4000,
      positionClass: 'toast-top-left'
    })
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
