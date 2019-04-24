import {Injectable} from '@angular/core';

const TOKEN_KEY = 'AuthToken';
const LOGIN_KEY = 'AuthUsername';
const AUTHORITIES_KEY = 'AuthAuthorities';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {

  private role: string = null;
  constructor(){

  }

  signOut(){
    window.sessionStorage.clear();
  }

  public saveToken(token: string){
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.setItem(TOKEN_KEY, token);
  }

  public getToken(): string{
    return sessionStorage.getItem(TOKEN_KEY);
  }

  public saveLogin(username: string){
    window.sessionStorage.removeItem(LOGIN_KEY);
    window.sessionStorage.setItem(LOGIN_KEY, username);
  }

  public getLogin(): string{
    return sessionStorage.getItem(LOGIN_KEY);
  }

  public saveAuthorities(authority: string){
    window.sessionStorage.removeItem(AUTHORITIES_KEY);
    window.sessionStorage.setItem(AUTHORITIES_KEY, authority);
  }

  public getAuthorities(): string {
    if (sessionStorage.getItem(TOKEN_KEY)){
      this.role = sessionStorage.getItem(AUTHORITIES_KEY);
    }
    return this.role;
  }
}
