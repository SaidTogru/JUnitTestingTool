package AlleTestklassen;
import java.awt.Image;
import java.util.EnumSet;

import turban.utils.*;
enum HSPersonalType implements IGuifiable{
	Student ("Student"), Professor ("Professor"), Tutor ("Tutor"), President ("Präsident"),Dean ("Dekan"), ITAdmin ("IT Admin"), Secretary ("Sekretär");

	private String guiString;
	private HSPersonalType(String guiString) {
		this.guiString=guiString;
	}
	@Override
	public Image getGuiIcon() {
		
		return null;
	}

	@Override
	public String toGuiString() {
		
		return guiString;
	}
	
	static EnumSet<HSPersonalType> receiveLessons = EnumSet.of(HSPersonalType.Student);
	static EnumSet<HSPersonalType> giveLessons = EnumSet.of(HSPersonalType.Professor,HSPersonalType.Tutor,HSPersonalType.President,HSPersonalType.Dean);
	static EnumSet<HSPersonalType> hasOrgResp = EnumSet.of(HSPersonalType.President,HSPersonalType.Dean);
	private static final EnumSet<HSPersonalType> isAdminStaff= EnumSet.of(Secretary,ITAdmin);
	 
	public boolean receiveLessons() {
		return receiveLessons.contains(this);
	}
	public boolean giveLessons() {
		return giveLessons.contains(this);
	}
	public boolean hasOrgResp() {
		return hasOrgResp.contains(this);
	}
	public boolean isAdminStaff() {
		return isAdminStaff.contains(this);
	}
	
	
	
}


