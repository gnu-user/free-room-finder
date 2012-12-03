<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/">
    <html>

      <head>
        <meta http-equiv="Content-Language" content="en-us"/>
          <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
            <title>Time Slots</title>
          </head>
      <body>
        <table border="1" width="100%" cellspacing="0" cellpadding="3" id="table1">
            <tr>
              <td colspan="2" bgcolor="#8DB6CD">
                <font face="Arial" size="5" color="#000000">
                  Room
                </font>
              </td>
              <td colspan="2" bgcolor="#8DB6CD">
                <font face="Arial" size="5" color="#000000">
                  Campus 
                </font>
              </td>
              <td colspan="2" bgcolor="#8DB6CD">
                <font face="Arial" size="5" color="#000000">
                  Start Time
                </font>
              </td>
              <td colspan="2" bgcolor="#8DB6CD">
                <font face="Arial" size="5" color="#000000">
                  End Time
                </font>
              </td>
              <td colspan="2" bgcolor="#8DB6CD">
                <font face="Arial" size="5" color="#000000">
                  Date
                </font>
              </td>
              <td colspan="2" bgcolor="#8DB6CD">
                <font face="Arial" size="5" color="#000000">
                  Request Number
                </font>
              </td>
            </tr>
      	<xsl:for-each select="TimeSlots/TimeSlot">
           <tr>
              <td colspan="2" bgcolor="#FFFFFF">
                <font face="Arial" size="3" color="#000000">
                  <xsl:value-of select="room"/>
                </font>
              </td>
              <td colspan="2" bgcolor="#FFFFFF">
                <font face="Arial" size="3" color="#000000">
                  <xsl:value-of select="campus"/>  
                </font>
              </td>
              <td colspan="2" bgcolor="#FFFFFF">
                <font face="Arial" size="3" color="#000000">
                  <xsl:value-of select="starttime"/>
                </font>
              </td>
              <td colspan="2" bgcolor="#FFFFFF">
                <font face="Arial" size="3" color="#000000">
                  <xsl:value-of select="endtime"/>
                </font>
              </td>
              <td colspan="2" bgcolor="#FFFFFF">
                <font face="Arial" size="3" color="#000000">
                  <xsl:value-of select="date"/>
                </font>
              </td>
              <td colspan="2" bgcolor="#FFFFFF">
                <font face="Arial" size="3" color="#000000">
                  <xsl:value-of select="request_num_people"/>
                </font>
              </td>
            </tr>
          </xsl:for-each>
        </table>
     </body>
	</html>
</xsl:template>

</xsl:stylesheet>
