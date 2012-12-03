<?xml version="1.0" encoding="utf-8" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:template match="/">
    <html>

      <head>
        <meta http-equiv="Content-Language" content="en-us"/>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
        <title>Time Slots XML File Contents:</title>
      </head>

      <body>
          <br />
          <h1 align="center">Time Slots XML File Contents:</h1>
        
          <table border="1" width="100%" cellspacing="0" cellpadding="3" id="table1">
          <tr>
            <td bgcolor="#4040F0">
              <font face="Verdana" size="4" color="#E0E0E0">Room#</font>
            </td>
            <td bgcolor="#4040F0">
              <font face="Verdana" size="4" color="#E0E0E0">Start Time</font>
            </td>
            <td bgcolor="#4040F0">
              <font face="Verdana" size="4" color="#E0E0E0">End Time</font>
            </td>
            <td bgcolor="#4040F0">
              <font face="Verdana" size="4" color="#E0E0E0">Date</font>
            </td>
            <td bgcolor="#4040F0">
              <font face="Verdana" size="4" color="#E0E0E0">Requested Number of People</font>
            </td>
          </tr>
          <xsl:for-each select="TimeSlots/TimeSlot">

            <tr>
              
              <td bgcolor="#E3E3D5">
                <font face="Verdana" size="4" color="#4684C1">
                  <xsl:value-of select="room"/>
                </font>
              </td>

              <td bgcolor="#E3E3D5">
                <font face="Verdana" size="4" color="#4684C1">
                  <xsl:value-of select="starttime"/>
                </font>
              </td>

              <td bgcolor="#E3E3D5">
                <font face="Verdana" size="4" color="#4684C1">
                  <xsl:value-of select="endtime"/>
                </font>
              </td>

              <td bgcolor="#E3E3D5">
                <font face="Verdana" size="4" color="#4684C1">
                  <xsl:value-of select="date"/>
                </font>
              </td>

              <td bgcolor="#E3E3D5">
                <font face="Verdana" size="4" color="#4684C1">
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