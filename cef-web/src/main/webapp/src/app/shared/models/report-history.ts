export class ReportHistory {
  id: number;
  userId: string;
  userFullName: string;
  userRole: string;
  actionDate: Date;
  reportAction: string;
  attachmentId: number;
  fileName: string;
  comments: string;
  fileDeleted: boolean;
}
