export class SideNavItem {
  baseUrl: string;
  id: number;
  description: string;
  children: SideNavItem[];

  constructor(id: number, description: string, baseUrl: string, children: SideNavItem[]) {
    this.id = id;
    this.description = description;
    this.baseUrl = baseUrl;
    this.children = children;
  }

  get url(): string {
    if (this.id) {
      return `${this.baseUrl}/${this.id}`;
    }
    return this.baseUrl;
  }
}
