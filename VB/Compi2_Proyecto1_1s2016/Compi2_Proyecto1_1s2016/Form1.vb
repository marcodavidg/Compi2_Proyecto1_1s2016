Imports System.Net.Sockets
Imports System.IO

Public Class Form1
    Dim punteroText As Integer

    Private Sub Button1_Click(sender As Object, e As EventArgs)

    End Sub

    Shared Sub Connect(ByVal serverIP As String, ByVal message As String, ByVal tipoRequest As Int32)
        Dim output As String = ""
        message = message & Chr(tipoRequest)
        Try
            ' Create a TcpClient. 
            ' The client requires a TcpServer that is connected 
            ' to the same address specified by the server and port 
            ' combination. 
            Dim port As Int32 = 19999
            Dim client As New TcpClient(serverIP, port)

            ' Translate the passed message into ASCII and store it as a byte array. 
            Dim data(255) As [Byte]
            data = System.Text.Encoding.ASCII.GetBytes(message)

            ' Get a client stream for reading and writing. 
            ' Stream stream = client.GetStream(); 
            Dim stream As NetworkStream = client.GetStream()

            ' Send the message to the connected TcpServer. 
            stream.Write(data, 0, data.Length)

            ' Buffer to store the response bytes.
            data = New [Byte](255) {}

            ' String to store the response ASCII representation. 
            Dim responseData As String = String.Empty

            ' Read the first batch of the TcpServer response bytes. 
            Dim bytes As Int32 = stream.Read(data, 0, data.Length)
            responseData = System.Text.Encoding.ASCII.GetString(data, 0, bytes)
            MessageBox.Show(responseData)
            Form1.returnText.Text = responseData
            ' Close everything.
            stream.Close()
            client.Close()
        Catch e As ArgumentNullException
            output = "ArgumentNullException: " + e.ToString()
            Form1.returnText.Text = output
        Catch e As SocketException
            output = "SocketException: " + e.ToString()
            MessageBox.Show(output)
        End Try

    End Sub

    Private Sub Button2_Click(sender As Object, e As EventArgs)

    End Sub

    Private Sub ToolStrip1_ItemClicked(sender As Object, e As ToolStripItemClickedEventArgs) Handles ToolStrip1.ItemClicked

    End Sub

    Private Sub Form1_Load(sender As Object, e As EventArgs) Handles MyBase.Load
        newTab()
        punteroText = 0
    End Sub

    Private Sub ToolStripButton1_Click(sender As Object, e As EventArgs)

    End Sub

    Sub newTab()
        Dim richtext As New RichTextBox
        Dim edittab As New TabPage
        Dim lines As New LineNumbers_For_RichTextBox
        edittab.BackColor = Color.White
        richtext.Width = 650
        richtext.Height = 218
        richtext.Name = "RTB"
        richtext.Dock = DockStyle.Right
        lines.BackColor = Color.White
        lines.ParentRichTextBox = richtext
        lines.Dock = DockStyle.Left
        lines.Width = 20
        lines.Height = 218
        edittab.Text = "New Tab"
        edittab.Controls.Add(lines)
        edittab.Controls.Add(richtext)
        TabControl1.TabPages.Add(edittab)
        TabControl1.SelectTab(edittab)
    End Sub

    Private Sub ToolStripButton2_Click(sender As Object, e As EventArgs)

    End Sub

    Private Sub ToolStripButton4_Click(sender As Object, e As EventArgs)

    End Sub

    Private Sub ToolStripDropDownButton1_Click(sender As Object, e As EventArgs) Handles ToolStripDropDownButton1.Click

    End Sub

    Private Sub BuscarYReemplazarToolStripMenuItem_Click(sender As Object, e As EventArgs) Handles BuscarYReemplazarToolStripMenuItem.Click

    End Sub

    Private Sub SfasToolStripMenuItem_Click(sender As Object, e As EventArgs) Handles SfasToolStripMenuItem.Click
        newTab()
    End Sub

    Private Sub GdToolStripMenuItem_Click(sender As Object, e As EventArgs) Handles GdToolStripMenuItem.Click
        Dim txt As String
        OpenFileDialog1.Filter = "Text File | *.txt"
        If OpenFileDialog1.ShowDialog = DialogResult.OK Then
            txt = OpenFileDialog1.FileName
            Dim fileReader As String
            fileReader = My.Computer.FileSystem.ReadAllText(txt)
            TabControl1.SelectedTab.Controls("RTB").Text = fileReader
        End If
    End Sub

    Private Sub ToolStripButton3_Click(sender As Object, e As EventArgs)
    End Sub

    Private Sub LimpiarTextoToolStripMenuItem_Click(sender As Object, e As EventArgs) Handles LimpiarTextoToolStripMenuItem.Click
        TabControl1.SelectedTab.Controls("RTB").Text = ""
    End Sub

    Private Sub EnvianEntradaToolStripMenuItem_Click(sender As Object, e As EventArgs) Handles EnvianEntradaToolStripMenuItem.Click
        Dim entrada As String
        entrada = TabControl1.SelectedTab.Controls("RTB").Text
        ' In this code example, use a hard-coded 
        ' IP address and message. 
        Dim serverIP As String = "localhost"
        Dim message As String = "Calling the Socket Server on localhost port 19999 at " + DateTime.Now
        Connect(serverIP, entrada, 1)
    End Sub

    Private Sub ProbarConexionToolStripMenuItem_Click(sender As Object, e As EventArgs) Handles ProbarConexionToolStripMenuItem.Click
        ' In this code example, use a hard-coded 
        ' IP address and message. 
        Dim serverIP As String = "localhost"
        Dim message As String = "Calling the Socket Server on localhost port 19999 at " + DateTime.Now
        Connect(serverIP, message, 13)
    End Sub

    Private Sub GuardarDocumentoToolStripMenuItem_Click(sender As Object, e As EventArgs) Handles GuardarDocumentoToolStripMenuItem.Click
        saveFileDialog1.Filter = "Text File|*.txt"
        SaveFileDialog1.ShowDialog()
        Dim path As String
        path = SaveFileDialog1.FileName
        If path <> "" Then
            MsgBox(path)
            File.Create(path).Dispose()
            Dim entrada As String
            entrada = TabControl1.SelectedTab.Controls("RTB").Text
            My.Computer.FileSystem.WriteAllText(path, entrada, False)
        End If
        
    End Sub

    Private Sub ToolStripButton1_Click_1(sender As Object, e As EventArgs) Handles ToolStripButton1.Click
        SaveFileDialog1.Filter = "Text File|*.txt"
        SaveFileDialog1.ShowDialog()
        Dim path As String
        path = SaveFileDialog1.FileName
        If path <> "" Then
            MsgBox(path)
            File.Create(path).Dispose()
            My.Computer.FileSystem.WriteAllText(path, returnText.Text, False)
        End If

    End Sub

    Private Sub ToolStripButton3_Click_1(sender As Object, e As EventArgs) Handles ToolStripButton3.Click
        Dim old As String = ToolStripTextBox1.Text
        Dim replacetext As String = ToolStripTextBox2.Text
        If TabControl1.SelectedTab.Controls("RTB").Text.Length > 0 Then
            TabControl1.SelectedTab.Controls("RTB").Text = TabControl1.SelectedTab.Controls("RTB").Text.Replace(old, replacetext)
        End If
    End Sub

    Private Sub ToolStripButton2_Click_1(sender As Object, e As EventArgs) Handles ToolStripButton2.Click
        Dim old As String = ToolStripTextBox1.Text
        Dim a As RichTextBox
        Dim encontrado As Integer
        encontrado = 818000
        a = TabControl1.SelectedTab.Controls("RTB")
        If TabControl1.SelectedTab.Controls("RTB").Text.Length > 0 Then
            a.Select(punteroText, a.Text.Length)
            If punteroText >= a.Text.Length Then
                punteroText = 0
            Else
                encontrado = a.Find(old, punteroText, RichTextBoxFinds.MatchCase)
                If encontrado < 818000 Then
                    punteroText = old.Length + a.SelectionStart
                Else
                    encontrado = 818000
                    punteroText = 0
                    encontrado = a.Find(old, punteroText, RichTextBoxFinds.MatchCase)
                    If encontrado = 818000 Then
                        a.SelectedText = "zq6efasdf"
                        punteroText = 0
                    Else
                        punteroText = old.Length + a.SelectionStart
                    End If
                End If
            End If

        End If
    End Sub

    Private Sub ToolStripTextBox1_Click(sender As Object, e As EventArgs) Handles ToolStripTextBox1.Click

    End Sub

    Private Sub ToolStripTextBox1_TextChanged(sender As Object, e As EventArgs) Handles ToolStripTextBox1.TextChanged
        punteroText = 0
    End Sub
End Class
