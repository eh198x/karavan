<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:output indent="yes" method="xml" encoding="UTF-8"/>

  <xsl:template match="/">
    <articles>
      <xsl:for-each select="articles/article">
        <xsl:copy>
          <xsl:attribute name="id">
            <xsl:value-of select="position()"/>
          </xsl:attribute>
          <title>
            <xsl:value-of select="title"/>
          </title>
          <author>
            <xsl:apply-templates select="author/*"/>
          </author>
          <release-date>
            <xsl:value-of select="release-date"/>
          </release-date>
        </xsl:copy>
      </xsl:for-each>
    </articles>
  </xsl:template>

  <xsl:template match="author/firstname | author/lastname">
    <xsl:value-of select="."/>
  </xsl:template>

</xsl:stylesheet>
