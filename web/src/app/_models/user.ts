export interface User {
  id: number;
  username: string;
  firstName: string;
  lastName: string;
  role: string;
  createdAt: Date;
  lastLogin?: Date;
}
