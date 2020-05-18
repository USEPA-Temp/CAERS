export class ReportHistory {
  id: number;
  userId: string;
  userFullName: string;
  userRole: string;
  actionDate: Date;
  reportAction: string;
  reportAttachmentId: number;
  fileName: string;
  comments: string;
  fileDeleted: boolean;
}
