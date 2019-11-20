import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';

export class ContactTypeCode implements BaseCodeLookup {
  shortName: string;
  code: string;
  description: string;
}
