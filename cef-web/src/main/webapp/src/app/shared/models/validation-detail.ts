import { EntityType } from 'src/app/shared/enums/entity-type';

export class ValidationDetail {
  id: number;
  identifier: string;
  type: EntityType;
  description: string;
  parents: ValidationDetail[];

}
