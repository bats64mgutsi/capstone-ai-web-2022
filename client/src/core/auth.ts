const TOKEN_KEY = "token";

export function setAccessToken(token: string): void {
  localStorage.setItem(TOKEN_KEY, token);
}

export function getAccessToken(): string | null {
  return localStorage.getItem(TOKEN_KEY);
}

export function isAuthentication(): boolean {
  const token = getAccessToken();
  return !!token;
}

export function deleteAccessToken(): void {
  localStorage.removeItem(TOKEN_KEY);
}