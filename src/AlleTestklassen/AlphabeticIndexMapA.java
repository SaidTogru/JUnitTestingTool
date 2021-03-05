package AlleTestklassen;

import java.util.*;

import turban.utils.ErrorHandler;



public class AlphabeticIndexMapA //implements IDebugable, IGuifiable
{
	
    List<String> _lstIndex=new ArrayList<String>(); 
	List<Object> _lstValues=new ArrayList<Object>();

	/* Diese Methode zeigt, wie man eine Methoden schreiben kÃķnnte, um schnell ein paar Werte in AlphabeticIndexMap zu fÃžllen:
	public void fillInSomeValuesForTesting()
	{
		_lstIndex.add ("A1"); _lstValues.add("Some String");
		_lstIndex.add ("B2");  _lstValues.add(new Object());
		
		List<String> lstSomeList=new ArrayList<String>();
		lstSomeList.add("Bla");
		lstSomeList.add("Blub");
		lstSomeList.add("BlaBla");
		
		_lstIndex.add ("Some List"); _lstValues.add(lstSomeList);
		
	}
	*/
	
	
	public String toJTextAreaString() { //damit die Strings angezeigt werden
		return _lstIndex.get(0);
	}
	
	//2b
		public String toDebugString() throws Exception { //für die Ausgabe
			List <String> tmp = new ArrayList<String>();
			for (int i = 0; i<_lstIndex.size();i++) {
			if(i >= _lstValues.size()) {
			 tmp.add("AlphabeticIndexMap: \n" + _lstIndex.get(i)+"\t"+"_lstValues.get(i) >fehlt!<\n");
			}
			else {
				 tmp.add("AlphabeticIndexMap: \n" + _lstIndex.get(i)+"\t"+_lstValues.get(i) +"\n");
			}
			}		
			if(_lstIndex.size() == 0) {
				return "keine Daten!";
			}
			return tmp.toString();
		}
	
	
	
	private void put_insertAtIndex(String strIndex, Object obj, int index)
	{
		_lstIndex.add(index,strIndex);
		_lstValues.add(index,obj);
	}
	
	public int put (String strIndex, Object obj) {
		ErrorHandler.Assert(strIndex!=null,true,AlphabeticIndexMapA.class,"Invalid parameter: strIndex==null");
		ErrorHandler.Assert(strIndex.length()>0,true,AlphabeticIndexMapA.class,"Invalid parameter: strIndex is empty!"  );
		ErrorHandler.Assert(obj!=null,true,AlphabeticIndexMapA.class,"Invalid parameter: obj==null!"  );
		
		int iIndexToReturn=-1;
		
		for (int i=0; i<_lstIndex.size(); i++)
		{
			String strCurIndex=_lstIndex.get(i);
			if (strCurIndex.compareTo(strIndex)>=0 ) //Add when strCurIndex>=strIndex the first time
			{	
				put_insertAtIndex( strIndex,  obj, i);
				iIndexToReturn=i;
				break;
			}
		}
			
	    if( iIndexToReturn==-1) //In case of empty list, or strIndex is greater than all entries in _lstIndex!
		{
			iIndexToReturn=_lstIndex.size(); //The index is then the end of the list==size()
			put_insertAtIndex( strIndex,  obj, iIndexToReturn); 
		}
		
		return iIndexToReturn;
	}

	//2h
	public int put1 (String strIndex, Object obj) throws Exception  {
		ErrorHandler.Assert(strIndex!=null,true,AlphabeticIndexMapA.class,"Invalid parameter: strIndex==null");
		ErrorHandler.Assert(strIndex.length()>0,true,AlphabeticIndexMapA.class,"Invalid parameter: strIndex is empty!"  );
		ErrorHandler.Assert(obj!=null,true,AlphabeticIndexMapA.class,"Invalid parameter: obj==null!"  );
		
		int iIndexToReturn=-1;
		
		for (int i=0; i<_lstIndex.size(); i++)
		{
			String strCurIndex=_lstIndex.get(i);
			if (strCurIndex.compareTo(strIndex)>=0 ) //Add when strCurIndex>=strIndex the first time
			{	
				put1_insertAtIndex( strIndex,  obj, i);
				iIndexToReturn=i;
				break;
			}
		}
			
	    if( iIndexToReturn==-1) //In case of empty list, or strIndex is greater than all entries in _lstIndex!
		{
			iIndexToReturn=_lstIndex.size(); //The index is then the end of the list==size()
			put1_insertAtIndex( strIndex,  obj, iIndexToReturn); 
		}
		
		return iIndexToReturn;
	}
	private void put1_insertAtIndex(String strIndex, Object obj, int index) throws Exception
	   {
			_lstIndex.add(index,strIndex);
			boolean blnTrue=true;
			if(blnTrue==true)
			{
				throw new Exception("Feeehler");
			}
			_lstValues.add(index,obj);
		}
	
	//2j 
	
	
	
	public int put2 (String strIndex, Object obj) throws Exception
 {
		ErrorHandler.Assert(strIndex!=null,true,AlphabeticIndexMapA.class,"Invalid parameter: strIndex==null!"  );
		ErrorHandler.Assert(strIndex.length()>0,true,AlphabeticIndexMapA.class,"Invalid parameter: strIndex is empty!"  );
		ErrorHandler.Assert(obj!=null,true,AlphabeticIndexMapA.class,"Invalid parameter: obj==null!"  );
	
		
		try
		{
			int iIndexToReturn=-1;
			
			for (int i=0; i<_lstIndex.size(); i++)
			{
				String strCurIndex=_lstIndex.get(i);
				if (strCurIndex.compareTo(strIndex)>=0 ) 
				{	
					put2_insertAtIndex( strIndex,  obj, i); 
					iIndexToReturn=i;
					break;
				}
			}
			
			if( iIndexToReturn==-1) 
			{
				iIndexToReturn=_lstIndex.size();
				put1_insertAtIndex( strIndex,  obj, iIndexToReturn); 
			}
			
			
		    return iIndexToReturn;
		}
		catch (Exception ex) {
			ErrorHandler.logException(ex,false,AlphabeticIndexMapA.class,"Error in put2 ({0} , {1}) in [{2}].",strIndex,obj,this);
			
			put2_bringBackToSafeStateIfNecessary(strIndex,obj);
			
			throw ex; 
		}
	} 
	
	//2j
	private void put2_insertAtIndex(String strIndex, Object obj, int index) throws Exception
	{
			_lstIndex.add(index,strIndex);
			boolean blnTrue=true;
			if(blnTrue==true)
			{
				throw new Exception("Simulated Exception");
			}
			_lstValues.add(index,obj);
		}
	
	//2j
	public void put2_bringBackToSafeStateIfNecessary(String strIndex,Object obj)
	{
		if (_lstIndex.size()!=_lstValues.size()) 
			{
				_lstIndex.remove(strIndex);
				_lstValues.remove(obj);
				ErrorHandler.Assert(_lstIndex.size()==_lstValues.size(),true,true, AlphabeticIndexMapA.class, "Severe consistency in object [{0}] detected! Not repairable!", this);
			}
	}

	
	
} 